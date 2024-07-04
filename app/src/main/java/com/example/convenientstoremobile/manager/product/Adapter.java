package com.example.convenientstoremobile.manager.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.convenientstoremobile.R;
import com.example.convenientstoremobile.model.Product;

import java.util.List;

public class Adapter extends BaseAdapter {
    private List<Product> mList;
    private Context mContext;

    public Adapter(List<Product> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Product getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            //create a blank view
//            convertView= LayoutInflater.from(mContext).inflate(R.layout.activity_item_view,parent,false);
            //refer ids of item_view
            holder=new ViewHolder();
//            holder.pImage=convertView.findViewById(R.id.pImage);
//            holder.pName=convertView.findViewById(R.id.pName);
//            holder.pPrice=convertView.findViewById(R.id.pPrice);
            convertView.setTag(holder);//create a template for later
        }
        else {//if exists view -> get view
            holder=(ViewHolder) convertView.getTag();
        }
        //set data
        Product product=mList.get(position);
        if(product!=null){
            holder.pImage.setImageResource(R.drawable.f96);
            holder.pName.setText(product.getName());
            holder.pPrice.setText(String.valueOf(product.getPrice()));
        }

        return convertView;
    }

    static class ViewHolder{
        ImageView pImage;
        TextView pName, pPrice;
    }
}

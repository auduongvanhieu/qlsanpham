package com.exam.hakhiem.productsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.exam.hakhiem.productsapp.Model.Product;
import com.exam.hakhiem.productsapp.R;
import com.exam.hakhiem.productsapp.View.AddProductActivity;
import com.exam.hakhiem.productsapp.View.AdminActivity;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {

    private ArrayList<Product> listData;
    private LayoutInflater layoutInflater;
    public ProductListAdapter(Context aContext, ArrayList<Product> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.proName = v.findViewById(R.id.tv_name);
            holder.proPrice =  v.findViewById(R.id.tv_price);
            holder.proDate =  v.findViewById(R.id.tv_date);
            holder.proImg = v.findViewById(R.id.imv_product_image);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        Product pro = listData.get(position);
        holder.proName.setText(pro.getName());
        holder.proPrice.setText("$"+pro.getPrice());
        String dateText = AdminActivity.dateFormat.format(pro.getDate());
        holder.proDate.setText(dateText);
        holder.proImg.setImageResource(AdminActivity.GetResourceImage(pro.getImage()));
        return v;
    }
    static class ViewHolder {
        TextView proName;
        TextView proPrice;
        TextView proDate;
        ImageView proImg;
    }
}
package com.exam.hakhiem.productsapp.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.exam.hakhiem.productsapp.Adapter.ProductListAdapter;
import com.exam.hakhiem.productsapp.Database.DbHelper;
import com.exam.hakhiem.productsapp.Model.Product;
import com.exam.hakhiem.productsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AdminActivity extends AppCompatActivity {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    ArrayList<Product> listProduct;

    public static String[] listImageName = {
            "img_sample_1","img_sample_2","img_sample_3","img_sample_4","img_sample_5",
            "img_sample_6","img_sample_7","img_sample_8","img_sample_9","img_sample_10"};
    public static int[] listImageResource = {
            R.drawable.img_sample_1,R.drawable.img_sample_2,R.drawable.img_sample_3,R.drawable.img_sample_4,R.drawable.img_sample_5,
            R.drawable.img_sample_6,R.drawable.img_sample_7,R.drawable.img_sample_8,R.drawable.img_sample_9,R.drawable.img_sample_10};

    ListView lvProduct;
    ProductListAdapter adapter;
    Button btnGoAdd;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        InitView();
        db = new DbHelper(AdminActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetupData();
    }

    private void InitView(){
        lvProduct = findViewById(R.id.lv_product);
        btnGoAdd = findViewById(R.id.btn_go_add);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        btnGoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AddProductActivity.class));
            }
        });
    }

    private void SetupData() {
        listProduct = new ArrayList<>();
        listProduct.addAll(db.getListProduct());
        adapter = new ProductListAdapter(AdminActivity.this,listProduct);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // custom dialog
                final Dialog dialog = new Dialog(AdminActivity.this);
                dialog.setContentView(R.layout.dialog_edit_product);
                Button btnUpdate = dialog.findViewById(R.id.btn_dialog_edit);
                Button btnDelete = dialog.findViewById(R.id.btn_dialog_delete);
                Button btnCancel = dialog.findViewById(R.id.btn_dialog_cancel);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminActivity.this,UpdateProductActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("product",listProduct.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteProduct(listProduct.get(position));
                        listProduct.clear();
                        listProduct.addAll(db.getListProduct());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    public static int GetResourceImage(String imgName){
        Log.d("image_name", imgName);
        for (int i=0; i<listImageName.length; i++){
            if (listImageName[i].equals(imgName))
                return listImageResource[i];
        }
        return listImageResource[0];
    }
}

package com.exam.hakhiem.productsapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    public static String[] listImageName = {
            "img_sample_1","img_sample_2","img_sample_3","img_sample_4","img_sample_5",
            "img_sample_6","img_sample_7","img_sample_8","img_sample_9","img_sample_10"};
    public static int[] listImageResource = {
            R.drawable.img_sample_1,R.drawable.img_sample_2,R.drawable.img_sample_3,R.drawable.img_sample_4,R.drawable.img_sample_5,
            R.drawable.img_sample_6,R.drawable.img_sample_7,R.drawable.img_sample_8,R.drawable.img_sample_9,R.drawable.img_sample_10};

    ListView lvProduct;
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
        ArrayList<Product> listProduct = new ArrayList<>();
        listProduct.add(new Product("P0001","Iphone 6s",500,"img_sample_1",new Date("01/02/2019")));
//        listProduct.add(new Product("P0001","Iphone 6s",500,"img_sample_1",new Date("02/02/2019")));
//        listProduct.add(new Product("P0001","Iphone 6s",500,"img_sample_1",new Date("03/02/2019")));
//        listProduct.add(new Product("P0001","Iphone 6s",500,"img_sample_1",new Date("04/02/2019")));
//        listProduct.add(new Product("P0001","Iphone 6s",500,"img_sample_1",new Date("05/02/2019")));
//        listProduct.add(new Product("P0001","Iphone 6s",500,"img_sample_1",new Date("06/02/2019")));
//        listProduct.add(new Product("P0001","Iphone 6s",500,"img_sample_1",new Date("07/02/2019")));
        listProduct.addAll(db.getListProduct());
        lvProduct.setAdapter(new ProductListAdapter(AdminActivity.this,listProduct));
    }
}

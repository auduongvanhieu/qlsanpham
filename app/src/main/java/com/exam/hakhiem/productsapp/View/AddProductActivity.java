package com.exam.hakhiem.productsapp.View;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.exam.hakhiem.productsapp.Database.DbHelper;
import com.exam.hakhiem.productsapp.Model.Product;
import com.exam.hakhiem.productsapp.R;

import java.util.Date;

public class AddProductActivity extends AppCompatActivity {
    EditText edtProductId, edtName, edtPrice, edtImg, edtDate;
    Button btnChooseImg, btnChooseDate, btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        InitView();
    }

    private void InitView(){
        edtProductId = findViewById(R.id.edt_add_product_id);
        edtName = findViewById(R.id.edt_add_name);
        edtPrice = findViewById(R.id.edt_add_price);
        edtImg = findViewById(R.id.edt_add_choose_img);
        edtDate = findViewById(R.id.edt_add_choose_date);
        btnChooseImg = findViewById(R.id.btn_add_choose_img);
        btnChooseDate = findViewById(R.id.btn_add_choose_date);
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_add_cancel);

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(AddProductActivity.this);
                dialog.setContentView(R.layout.dialog_list_image_name);
                ListView lvImgName = dialog.findViewById(R.id.lv_image_name);
                ArrayAdapter adapterImgName = new ArrayAdapter<String>(AddProductActivity.this, android.R.layout.simple_list_item_1, AdminActivity.listImageName);
                lvImgName.setAdapter(adapterImgName);
                lvImgName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edtImg.setText(AdminActivity.listImageName[position]);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDate.setText("08/30/2019");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(AddProductActivity.this);

                String productId = edtProductId.getText().toString();
                String name = edtName.getText().toString();
                Float price = Float.parseFloat(edtPrice.getText().toString());
                String image = edtImg.getText().toString();
                Date date = new Date(edtDate.getText().toString());

                Product product = new Product(productId, name, price, image, date);

                db.insertProduct(product);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}

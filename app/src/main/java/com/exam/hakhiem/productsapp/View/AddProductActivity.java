package com.exam.hakhiem.productsapp.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.exam.hakhiem.productsapp.Database.DbHelper;
import com.exam.hakhiem.productsapp.Model.Product;
import com.exam.hakhiem.productsapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProductActivity extends AppCompatActivity {
    EditText edtProductId, edtName, edtPrice, edtImg, edtDate;
    Button btnChooseImg, btnChooseDate, btnAdd, btnCancel;
    DatePickerDialog picker;
    String dateProduct="03/03/2020";
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
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddProductActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                dateProduct = (monthOfYear + 1) + "/" + (dayOfMonth + 1) + "/" + year;
                                picker.dismiss();
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check validate
                String productId = edtProductId.getText().toString();
                String name = edtName.getText().toString();
                String image = edtImg.getText().toString();

                if (!CheckProductId(productId)){
                    return;
                }
                if (!CheckName(name)){
                    return;
                }
                if (edtPrice.getText().toString().isEmpty()){
                    Toast.makeText(AddProductActivity.this,"Bạn chưa điền giá",Toast.LENGTH_LONG).show();
                    return;
                }
                Float price = Float.parseFloat(edtPrice.getText().toString());
                if (!CheckPrice(price)){
                    return;
                }
                if (!CheckImage(image)){
                    return;
                }
                if (edtDate.getText().toString() == ""){
                    Toast.makeText(AddProductActivity.this,"Bạn chưa chọn ngày",Toast.LENGTH_LONG).show();
                    return;
                }
                Date date = new Date(dateProduct);
                if (!CheckDate(date)){
                    return;
                }

                // Save to database
                DbHelper db = new DbHelper(AddProductActivity.this);

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

    public boolean CheckProductId(String productId){
        if (productId.isEmpty()){
            Toast.makeText(AddProductActivity.this,"Bạn chưa nhập Id sản phẩm",Toast.LENGTH_LONG).show();
            return false;
        }

        Pattern pattern = Pattern.compile("SP[0-9][0-9][0-9][0-9]");
        Matcher matcher = pattern.matcher(productId);

        if (!matcher.matches()){
            Toast.makeText(AddProductActivity.this,"Mã sản phẩm phải bắt đầu bằng SP và 4 ký tự số",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckName(String name){
        if (name.isEmpty()){
            Toast.makeText(AddProductActivity.this,"Bạn chưa nhập Tên sản phẩm",Toast.LENGTH_LONG).show();
            return false;
        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s]*");
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()){
            Toast.makeText(AddProductActivity.this,"Tên sản phẩm không được chứa ký tự đặc biệt",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckPrice(Float price){
        if (price<200 || price>800){
            Toast.makeText(AddProductActivity.this,"Giá sản phẩm phải lớn hơn 200 và bé hơn 800",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckImage(String img){
        if (img.isEmpty()){
            Toast.makeText(AddProductActivity.this,"Bạn chưa hình",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckDate(Date date){
        Date currentDate = new Date();
        currentDate.setDate(currentDate.getDate()+1);
        if (date.after(currentDate)){
            Toast.makeText(AddProductActivity.this,"Ngày đăng không được lớn hơn ngày hiện tại",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

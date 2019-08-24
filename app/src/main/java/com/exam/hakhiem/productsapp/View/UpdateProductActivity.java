package com.exam.hakhiem.productsapp.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.exam.hakhiem.productsapp.Database.DbHelper;
import com.exam.hakhiem.productsapp.Model.Product;
import com.exam.hakhiem.productsapp.R;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProductActivity extends AppCompatActivity {
    EditText edtProductId, edtName, edtPrice, edtImg, edtDate;
    Button btnChooseImg, btnChooseDate, btnAdd, btnCancel;
    public final String DATE_FORMAT = "MM/dd/yyyy";
    public SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    DatePickerDialog picker;
    String dateProduct="03/03/2020";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        InitView();
    }

    private void InitView() {
        edtProductId = findViewById(R.id.edt_update_product_id);
        edtName = findViewById(R.id.edt_update_name);
        edtPrice = findViewById(R.id.edt_update_price);
        edtImg = findViewById(R.id.edt_update_choose_img);
        edtDate = findViewById(R.id.edt_update_choose_date);
        btnChooseImg = findViewById(R.id.btn_update_choose_img);
        btnChooseDate = findViewById(R.id.btn_update_choose_date);
        btnAdd = findViewById(R.id.btn_update);
        btnCancel = findViewById(R.id.btn_update_cancel);

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(UpdateProductActivity.this);
                dialog.setContentView(R.layout.dialog_list_image_name);
                ListView lvImgName = dialog.findViewById(R.id.lv_image_name);
                ArrayAdapter adapterImgName = new ArrayAdapter<String>(UpdateProductActivity.this, android.R.layout.simple_list_item_1, AdminActivity.listImageName);
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
                picker = new DatePickerDialog(UpdateProductActivity.this,
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
                    Toast.makeText(UpdateProductActivity.this,"Bạn chưa điền giá",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(UpdateProductActivity.this,"Bạn chưa chọn ngày",Toast.LENGTH_LONG).show();
                    return;
                }
                Date date = new Date(dateProduct);
                if (!CheckDate(date)){
                    return;
                }

                // Save to database
                DbHelper db = new DbHelper(UpdateProductActivity.this);

                Product product = new Product(productId, name, price, image, date);

                db.updateProduct(product);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Load data from before screen
        Product pro = (Product) getIntent().getSerializableExtra("product");
        edtProductId.setText(pro.getProductId());
        edtName.setText(pro.getName());
        edtPrice.setText(pro.getPrice()+"");
        edtImg.setText(pro.getImage());
        String date="01/01/2000";
        try {
            date = AdminActivity.dateFormat.format(pro.getDate());
            dateProduct = dateFormat.format(pro.getDate());
        } catch (Exception e){}
        edtDate.setText(date);
    }

    public boolean CheckProductId(String productId){
        if (productId.isEmpty()){
            Toast.makeText(UpdateProductActivity.this,"Bạn chưa nhập Id sản phẩm",Toast.LENGTH_LONG).show();
            return false;
        }

        Pattern pattern = Pattern.compile("SP[0-9][0-9][0-9][0-9]");
        Matcher matcher = pattern.matcher(productId);

        if (!matcher.matches()){
            Toast.makeText(UpdateProductActivity.this,"Mã sản phẩm phải bắt đầu bằng SP và 4 ký tự số",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckName(String name){
        if (name.isEmpty()){
            Toast.makeText(UpdateProductActivity.this,"Bạn chưa nhập Tên sản phẩm",Toast.LENGTH_LONG).show();
            return false;
        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s]*");
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()){
            Toast.makeText(UpdateProductActivity.this,"Tên sản phẩm không được chứa ký tự đặc biệt",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckPrice(Float price){
        if (price<200 || price>800){
            Toast.makeText(UpdateProductActivity.this,"Giá sản phẩm phải lớn hơn 200 và bé hơn 800",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckImage(String img){
        if (img.isEmpty()){
            Toast.makeText(UpdateProductActivity.this,"Bạn chưa hình",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean CheckDate(Date date){
        Date currentDate = new Date();
        currentDate.setDate(currentDate.getDate()+1);
        if (date.after(currentDate)){
            Toast.makeText(UpdateProductActivity.this,"Ngày đăng không được lớn hơn ngày hiện tại",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

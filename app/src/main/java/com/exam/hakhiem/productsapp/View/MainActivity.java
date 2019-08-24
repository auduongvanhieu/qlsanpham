package com.exam.hakhiem.productsapp.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.exam.hakhiem.productsapp.R;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    CheckBox chbRemember;
    Button btnLogin, btnCancel;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetupView();
    }

    private void SetupView(){
        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        chbRemember = findViewById(R.id.chb_remember);
        btnLogin = findViewById(R.id.btn_login);
        btnCancel = findViewById(R.id.btn_cancel);

        edtUsername.setText(pref.getString("username", ""));
        edtPassword.setText(pref.getString("password", ""));
        if (!pref.getString("username", "").isEmpty())
            chbRemember.setChecked(true);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtUsername.getText().toString().equals("fpoly")||!edtPassword.getText().toString().equals("poly123")){
                    Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác",Toast.LENGTH_LONG).show();
                    return;
                }
                if (chbRemember.isChecked()){
                    editor.putString("username", edtUsername.getText().toString());
                    editor.putString("password", edtPassword.getText().toString());
                    editor.commit(); // commit changes
                }
                else {
                    editor.putString("username", "");
                    editor.putString("password", "");
                    editor.commit(); // commit changes
                }
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                Toast.makeText(MainActivity.this, "Đăng nhập thành công",Toast.LENGTH_LONG).show();
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

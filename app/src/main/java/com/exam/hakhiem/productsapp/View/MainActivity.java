package com.exam.hakhiem.productsapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.exam.hakhiem.productsapp.R;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    CheckBox chbRemember;
    Button btnLogin, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetupView();
    }

    private void SetupView(){
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_username);
        chbRemember = findViewById(R.id.chb_remember);
        btnLogin = findViewById(R.id.btn_login);
        btnCancel = findViewById(R.id.btn_cancel);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            }
        });
    }
}

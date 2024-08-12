package com.example.asm_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_2.Activity.DatabaseSQLite.UserDatabase;
import com.example.asm_2.R;

public class RegisterPageActivity extends AppCompatActivity {
    private EditText edtRegisterYourName, edtRegisterYourEmail, edtRegisterYourPhone, edtRegisterYourPassword;
    private Button btnRegisterToDashBoard;
    public UserDatabase userDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        edtRegisterYourName = findViewById(R.id.edtRegisterYourName);
        edtRegisterYourEmail =findViewById(R.id.edtRegisterYourEmail);
        edtRegisterYourPhone = findViewById(R.id.edtRegisterYourPhone);
        edtRegisterYourPassword = findViewById(R.id.edtRegisterYourPassword);
        btnRegisterToDashBoard = findViewById(R.id.btnRegisterToDashBoard);
        userDatabase= new UserDatabase(RegisterPageActivity.this);
        btnRegisterToDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
                Intent intent =new Intent(RegisterPageActivity.this, StartingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signUp(){
        String user = edtRegisterYourName.getText().toString().trim();
        String password = edtRegisterYourPassword.getText().toString().trim();
        String email = edtRegisterYourEmail.getText().toString().trim();
        String phone = edtRegisterYourPhone.getText().toString().trim();
        if (TextUtils.isEmpty(user)){
            edtRegisterYourPhone.setError("Username cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(password)){
            edtRegisterYourPassword.setError("Password can not be empty");
            return;
        }
        if (TextUtils.isEmpty(email)){
            edtRegisterYourEmail.setError("Email cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(email)){
            edtRegisterYourPhone.setError("Phone cannot be empty");
            return;
        }
        long insert = userDatabase.addNewUser(user, password, email,phone);
        if (insert == -1){
            //loi
            Toast.makeText(RegisterPageActivity.this, "Insert Fail", Toast.LENGTH_LONG).show();
        }else {
            //thanh cong
            Toast.makeText(RegisterPageActivity.this, "Insert Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}

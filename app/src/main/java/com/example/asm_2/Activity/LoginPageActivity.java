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
import com.example.asm_2.Activity.Models.User;
import com.example.asm_2.R;

public class LoginPageActivity extends AppCompatActivity {
    private Button btnLogIn
    private EditText edtEmail, edtPass;
    public UserDatabase userDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);
        btnLogIn = findViewById(R.id.btnLoginToDashboard);
        edtEmail = findViewById(R.id.edtStartEmail);
        edtPass  =findViewById(R.id.edtStartPassword);
        userDatabase = new UserDatabase(LoginPageActivity.this);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Email cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edtPass.setError("Password cannot be empty");
                    return;
                }
                User data = userDatabase.getInfoUser(email, pass);
                assert data != null;
                if (data.getEmail()!=null){
                    //thanh cong
                    String gmail = data.getEmail();
                    Intent intent = new Intent(LoginPageActivity.this, DashboardActivity.class);
                    Toast.makeText(LoginPageActivity.this, email, Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }else{
                    //dang nhap linh tinh
                    Toast.makeText(LoginPageActivity.this, "Account Invalid", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}

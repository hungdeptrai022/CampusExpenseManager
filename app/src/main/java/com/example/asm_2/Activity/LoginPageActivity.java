package com.example.campusexpensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPageActivity extends AppCompatActivity {
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);
        Button btnExitFromLogin = findViewById(R.id.btnExitFromLogin);
        Button btnLoginToDashBoard = findViewById(R.id.btnLoginToDashboard);
        btnExitFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPageActivity.this, StartingActivity.class);
                startActivity(intent);
            }
        });
        btnLoginToDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPageActivity.this, StartingActivity.class);
                startActivity(intent);
            }
        });
    }
}

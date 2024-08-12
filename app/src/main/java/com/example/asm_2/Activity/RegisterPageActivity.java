package com.example.campusexpensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterPageActivity extends AppCompatActivity {
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Button btnRegisterToDashBoard = findViewById(R.id.btnRegisterToDashBoard);
        Button btnExitFromRegister = findViewById(R.id.btnExitFromRegister);
        btnRegisterToDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageActivity.this, StartingActivity.class);
                startActivity(intent);
            }
        });
        btnExitFromRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageActivity.this, StartingActivity.class);
                startActivity(intent);
            }
        });
    }
}
